package org.mule.modules.ctg.transaction;

import org.mule.api.MuleContext;
import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionException;
import org.mule.api.transaction.UniversalTransactionFactory;
import org.mule.modules.ctg.connect.ConnectionManager;

public class CTGTransactionFactory implements UniversalTransactionFactory {

	@Override
	public Transaction beginTransaction(MuleContext muleCtx) throws TransactionException {
		// TODO Auto-generated method stub
		CTGTransaction tx = new CTGTransaction(muleCtx);
		
		//tx.begin();
		
		return tx;
	}

	@Override
	public boolean isTransacted() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Transaction createUnboundTransaction(MuleContext muleCtx) throws TransactionException {
		// TODO Auto-generated method stub
		return new CTGTransaction(muleCtx);
	}

}
