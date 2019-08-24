package cn.hunkier.spring.data.mybatisdemo.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  在 Money 与 Long 之间转换的 TypeHandler ，处理 CNY 人民币
 */
public class MoneyTypeHandler extends BaseTypeHandler<Money> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
            ps.setLong(i, parameter.getAmountMajorInt());
    }

    @Override
    public Money getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Money getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Money getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }

    private Money parseMoney(Long value) {
        return  Money.of(CurrencyUnit.of("CNY"), value/100.00);
    }
}
