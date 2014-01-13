package com.technion.coolie.techtrade;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

public interface ITechoinsBankServerAPI {
	ReturnCode addAccount(BankAccount account, String password) throws IOException;

	ReturnCode removeAccount(BankAccount account) throws IOException;

	BankAccount getAccount(BankAccount account) throws JsonSyntaxException,IOException;

	List<TechoinsTransfer> getHistory(BankAccount account) throws JsonSyntaxException, IOException;

	ReturnCode moveMoney(TechoinsTransfer transfer) throws IOException;
}
