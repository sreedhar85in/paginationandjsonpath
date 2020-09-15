package com.tranxactor.pagination1.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Map;

import oracle.jdbc.OracleConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;

@Slf4j
@Repository
public class TransactionsRepository {

	@Autowired
	private JdbcTemplate template;

	public Map<String, Object> transaction(Integer transactionType, Long merchantId, String cardNum, String pin,
			Long extTransactionId, Long extTransactionRefundId, BigDecimal transactionValue, String ipAddress,
			Timestamp transDateInput, boolean userInput, int traderTransIdentType, boolean redemption, Object[] transArray,Object[] tenderArray) {

		SimpleJdbcCall call = new SimpleJdbcCall(template).withProcedureName("Pr_Trans_Insert").declareParameters(
				new SqlParameter("p_thor_trans_type_id", Types.INTEGER),
				new SqlParameter("p_from_trader_id", Types.BIGINT), new SqlParameter("p_password", Types.VARCHAR),
				new SqlParameter("p_to_trader_trans_ident", Types.VARCHAR),
				new SqlParameter("p_to_trader_trans_ident_type", Types.INTEGER),
				new SqlParameter("p_ext_transaction_id", Types.BIGINT),
				new SqlParameter("p_ext_refunded_transaction_id", Types.BIGINT),
				new SqlParameter("p_transaction_date", Types.TIMESTAMP),
				new SqlParameter("p_transaction_value", Types.NUMERIC),
				new SqlParameter("p_credit_value", Types.NUMERIC), new SqlParameter("p_loyalty_value", Types.NUMERIC),
				new SqlParameter("p_voucher_value", Types.NUMERIC), new SqlParameter("p_ip_address", Types.VARCHAR),
				new SqlParameter("p_trans_details", OracleTypes.ARRAY, "OBJ_TAB_TRANS_DETAIL_EXT"),
				new SqlParameter("p_tender_details", OracleTypes.ARRAY, "OBJ_TAB_TENDER_DETAIL1"),
				new SqlOutParameter("p_transaction_id", Types.BIGINT),
				new SqlOutParameter("p_parent_trans_responses", OracleTypes.ARRAY, "OBJ_TAB_TRANS_RESPONSE"),
				new SqlOutParameter("p_child_trans_responses", OracleTypes.ARRAY, "OBJ_TAB_TRANS_RESPONSE"),
				new SqlOutParameter("p_trans_campaign_messages", OracleTypes.ARRAY, "OBJ_TAB_TRANS_MESSAGE"),
				new SqlOutParameter("p_credit_balance", Types.NUMERIC),
				new SqlOutParameter("p_loyalty_balance", Types.NUMERIC),
				new SqlOutParameter("p_loyalty_earned", Types.NUMERIC));

		LocalDateTime now = LocalDateTime.now();
		Timestamp transDate = Timestamp.valueOf(now);
		BigDecimal loyaltyValue = new BigDecimal(0);

		if (userInput) {

			transDate = transDateInput;

		}
		
		if(redemption) {
			
			loyaltyValue = transactionValue;
		}

		SqlTypeValue trans = new AbstractSqlTypeValue() {
			@Override
			protected Object createTypeValue(Connection connection, int arg1, String arg2) throws SQLException {
				return (connection.unwrap(OracleConnection.class)).createOracleArray("OBJ_TAB_TRANS_DETAIL_EXT",
						transArray);
			}
		};

		SqlTypeValue tender = new AbstractSqlTypeValue() {
			@Override
			protected Object createTypeValue(Connection connection, int arg1, String arg2) throws SQLException {
				return (connection.unwrap(OracleConnection.class)).createOracleArray("OBJ_TAB_TENDER_DETAIL1",
						tenderArray);
			}
		};
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("p_thor_trans_type_id", transactionType)
				.addValue("p_from_trader_id", merchantId).addValue("p_password", pin)
				.addValue("p_to_trader_trans_ident", cardNum).addValue("p_to_trader_trans_ident_type", traderTransIdentType)
				.addValue("p_ext_transaction_id", extTransactionId)
				.addValue("p_ext_refunded_transaction_id", extTransactionRefundId)
				.addValue("p_transaction_date", transDate).addValue("p_transaction_value", transactionValue)
				.addValue("p_credit_value", 0).addValue("p_loyalty_value", loyaltyValue).addValue("p_voucher_value", 0)
				.addValue("p_ip_address", ipAddress)
				.addValue("p_trans_details", trans)
				.addValue("p_tender_details", tender);
					log.info("Parameters sending to the Oracle Procedure are : " + paramMap.toString());
				return call.execute(paramMap);
	}
	
	
public Map<String, Object> checkotp(String token){
		
		
		SimpleJdbcCall call = new SimpleJdbcCall(template).withCatalogName("trans_token_pkg").withProcedureName("get_trans_token").declareParameters(
				new SqlParameter("p_token", Types.VARCHAR),
				new SqlOutParameter("p_ref_cursor", OracleTypes.CURSOR));
				//new SqlParameter("p_fl_raise", Types.NUMERIC));
		//token = "token3";
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("p_token", token);
				
		log.info("Parameters sending to the Oracle Procedure are : " + paramMap.toString());
		return call.execute(paramMap);
	}
}
