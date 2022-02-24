package com.transfers.payments.web.command.client;

import org.apache.log4j.Logger;
import com.transfers.payments.Path;
import com.transfers.payments.db.DBManager;
import com.transfers.payments.exception.AppException;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteDeferPaymentCmd extends Command {

    private static final long serialVersionUID = 77795462334L;

    private static final Logger LOG = Logger.getLogger(ReplenishBalanceCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        String paymentId = request.getParameter("payment_id");
        LOG.trace("found in request: paymentId --> " + paymentId);
        DBManager manager = DBManager.getInstance();
        try {
            LOG.trace("delete from db: by paymentId --> " + paymentId);
            manager.deletePayment(Long.parseLong(paymentId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOG.debug("Command finished");
        return Path.COMMAND_USER_PAYMENTS;
    }
}
