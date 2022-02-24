package com.transfers.payments.web.command.client;

import org.apache.log4j.Logger;
import com.transfers.payments.Path;
import com.transfers.payments.db.DBManager;
import com.transfers.payments.db.entity.Card;
import com.transfers.payments.db.entity.User;
import com.transfers.payments.db.enumeration.Request;
import com.transfers.payments.exception.AppException;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientRequestCmd extends Command {

    private static final long serialVersionUID = 7732123567234L;

    private static final Logger LOG = Logger.getLogger(ClientRequestCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        User user = (User) request.getSession().getAttribute("user");
        LOG.trace("get attribute from session" + user);
        DBManager manager = DBManager.getInstance();
        // get user cards list
        List<Card> cards = manager.getUserCards(user);
        LOG.trace("Found in DB: cardsList --> " + cards);
        List<Card> resultCards = new ArrayList<>();
        LOG.trace("create : resultCards --> " + resultCards);
        Request requestEnum;


        for (Card card : cards) {
            requestEnum = Request.getRequest(card);
            if (requestEnum == Request.TRUE) {
                resultCards.add(card);
            }
        }
        request.setAttribute("cards", resultCards);
        LOG.trace("setting attribut in session " + resultCards);
        LOG.debug("Command finished");
        return Path.PAGE_USER_REQUESTS;
    }
}
