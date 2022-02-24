package com.transfers.payments.web.command.client;

import org.apache.log4j.Logger;
import com.transfers.payments.Path;
import com.transfers.payments.db.Fields;
import com.transfers.payments.exception.AppException;
import com.transfers.payments.exception.Messages;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddBalanceCmd extends Command {

    private static final long serialVersionUID = 78542177478505L;

    private static final Logger LOG = Logger.getLogger(AddBalanceCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        String id = request.getParameter("card_id");
        String sum = request.getParameter("sum");
        LOG.trace("get parameters from request " + id + " " + sum);
        if (sum == null || id == null || id.isEmpty() || sum.isEmpty()) {
            throw new AppException(Messages.FIELDS_CANNOT_BE_EMPTY);
        }
        if (Integer.parseInt(sum) > Fields.PAYMENT_MAX_SUM) {
            throw new AppException(Messages.SUM_HIGHER_THAN_MAX);
        }
        HttpSession session = request.getSession();
        session.setAttribute("card_id", id);
        session.setAttribute("sum", sum);

        LOG.debug("Command finished");
        return Path.PAGE_USER_ACTION_ADD_BALANCE;
    }
}
