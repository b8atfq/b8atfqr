package com.transfers.payments.web.command.client;

import org.apache.log4j.Logger;
import com.transfers.payments.Path;
import com.transfers.payments.db.DBManager;
import com.transfers.payments.db.Fields;
import com.transfers.payments.db.entity.Card;
import com.transfers.payments.exception.AppException;
import com.transfers.payments.exception.Messages;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckPaymentCmd extends Command {

    private static final long serialVersionUID = 7851333478505L;

    private static final Logger LOG = Logger.getLogger(CheckPaymentCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        String id = request.getParameter("card_id");
        String sum = request.getParameter("sum");
        String destination = request.getParameter("destination");
        LOG.trace("get attribute from session");
        DBManager manager = DBManager.getInstance();
        if (sum == null || id == null || destination == null || id.isEmpty() || sum.isEmpty() || destination.isEmpty()) {
            throw new AppException(Messages.FIELDS_CANNOT_BE_EMPTY);
        }
        int sumPayment = Integer.parseInt(sum);
        if (sumPayment > Fields.PAYMENT_MAX_SUM) {
            throw new AppException(Messages.SUM_HIGHER_THAN_MAX);
        }
        if (destination.length() != Fields.CARDS_NUMBER) {
            throw new AppException(Messages.CARD_DESTINATION_IS_NOT_VALID);
        }
        Card card = manager.findCard(Integer.parseInt(id));
        LOG.trace("Found in DB: card --> " + card);
        if (card.getMoney() < sumPayment) {
            throw new AppException(Messages.SUM_HIGHER_THAN_MONEY);
        }
        Card destinationCard = manager.findCardByNumber(Long.parseLong(destination));
        LOG.trace("Found in DB: destinationCard --> " + destinationCard);
        if (destinationCard == null) {
            throw new AppException(Messages.DESTINATION_CARD_DOES_NOTE_EXISTS);
        }
        if (destinationCard.getActivityId() == 1) {
            throw new AppException(Messages.CARD_USER_WAS_BLOCKED);
        }

        HttpSession session = request.getSession();
        session.setAttribute("card_id", id);
        session.setAttribute("sum", sum);
        session.setAttribute("destination", destination);
        LOG.trace("setting attribute on session --> " + id + " " + sum + " " + destination);

        LOG.debug("Command finished");
        return Path.PAGE_USER_CONFIRM_PAYMENT;
    }
}
