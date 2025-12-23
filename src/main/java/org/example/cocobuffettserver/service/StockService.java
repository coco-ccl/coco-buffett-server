package org.example.cocobuffettserver.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.request.StockTradeRequest;
import org.example.cocobuffettserver.dto.response.MemberStockInfo;
import org.example.cocobuffettserver.dto.response.MemberStocksResponse;
import org.example.cocobuffettserver.dto.response.StockResponse;
import org.example.cocobuffettserver.entity.MemberEntity;
import org.example.cocobuffettserver.entity.MemberStockEntity;
import org.example.cocobuffettserver.entity.StockEntity;
import org.example.cocobuffettserver.enums.TradeType;
import org.example.cocobuffettserver.exception.CocoBuffettErrorCode;
import org.example.cocobuffettserver.exception.CocoBuffettException;
import org.example.cocobuffettserver.repository.MemberRepository;
import org.example.cocobuffettserver.repository.MemberStockRepository;
import org.example.cocobuffettserver.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StockService {

    StockRepository stockRepository;
    MemberRepository memberRepository;
    MemberStockRepository memberStockRepository;

    public List<StockResponse> getStockList() {
        List<StockEntity> stocks = stockRepository.findAll();

        return stocks.stream()
                .map(stock -> StockResponse.builder()
                        .name(stock.getName())
                        .ticker(stock.getTicker())
                        .currentPrice(stock.getCurrentPrice())
                        .build())
                .collect(Collectors.toList());
    }

    public MemberStocksResponse getMemberStocks(String memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.MEMBER_NOT_FOUND));

        List<MemberStockEntity> memberStocks = memberStockRepository.findByMember_MemberId(memberId);

        List<MemberStockInfo> stockInfos = memberStocks.stream()
                .map(memberStock -> MemberStockInfo.builder()
                        .name(memberStock.getStock().getName())
                        .ticker(memberStock.getStock().getTicker())
                        .quantity(memberStock.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return MemberStocksResponse.builder()
                .deposit(member.getBalance())
                .stocks(stockInfos)
                .build();
    }

    @Transactional
    public void trade(String memberId, StockTradeRequest request) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.MEMBER_NOT_FOUND));

        StockEntity stock = stockRepository.findById(request.getTicker())
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.STOCK_NOT_FOUND));

        if (request.getTradeType() == TradeType.BUY) {
            handleBuy(member, stock, request.getQuantity(), request.getPrice());
        } else if (request.getTradeType() == TradeType.SELL) {
            handleSell(member, stock, request.getQuantity(), request.getPrice());
        }
    }

    private void handleBuy(MemberEntity member, StockEntity stock, Integer quantity, Long price) {
        long totalPrice = price * quantity;

        if (member.getBalance() < totalPrice) {
            throw new CocoBuffettException(CocoBuffettErrorCode.INSUFFICIENT_BALANCE);
        }

        member.deductBalance(totalPrice);

        MemberStockEntity memberStock = memberStockRepository
                .findByMember_MemberIdAndStock_Ticker(member.getMemberId(), stock.getTicker())
                .orElse(null);

        if (memberStock != null) {
            memberStock.addQuantity(quantity);
        } else {
            memberStock = MemberStockEntity.builder()
                    .member(member)
                    .stock(stock)
                    .quantity(quantity)
                    .build();
            memberStockRepository.save(memberStock);
        }
    }

    private void handleSell(MemberEntity member, StockEntity stock, Integer quantity, Long price) {
        MemberStockEntity memberStock = memberStockRepository
                .findByMember_MemberIdAndStock_Ticker(member.getMemberId(), stock.getTicker())
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.INSUFFICIENT_STOCK));

        if (memberStock.getQuantity() < quantity) {
            throw new CocoBuffettException(CocoBuffettErrorCode.INSUFFICIENT_STOCK);
        }

        long totalPrice = price * quantity;

        memberStock.subtractQuantity(quantity);

        if (memberStock.getQuantity() == 0) {
            memberStockRepository.delete(memberStock);
        }

        member.addBalance(totalPrice);
    }
}
