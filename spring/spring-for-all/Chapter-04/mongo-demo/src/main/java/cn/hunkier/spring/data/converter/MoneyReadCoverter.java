package cn.hunkier.spring.data.converter;

import org.bson.Document;
import org.bson.json.StrictJsonWriter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

public class MoneyReadCoverter implements Converter<Document, Money> {
    @Override
    public Money convert(Document source) {
        Document money = (Document) source.get("money");
        final double amount = Double.parseDouble(money.getString("amount"));
        final String currency = ((Document) money.get("currency")).getString("code");
        return Money.of(CurrencyUnit.of(currency),amount);
    }
}
