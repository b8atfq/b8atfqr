package com.transfers.payments.web.command.admin;

import org.apache.log4j.Logger;
import com.transfers.payments.Path;
import com.transfers.payments.db.DBManager;
import com.transfers.payments.db.entity.Card;
import com.transfers.payments.exception.AppException;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RequestCmd extends Command {

    private static final long serialVersionUID = 7732235467234L;

    private static final Logger LOG = Logger.getLogger(RequestCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        // get user cards list
        DBManager manager = DBManager.getInstance();
        List<Card> cards = manager.getCardsWithRequest();

        LOG.trace("Found in DB: cardsList --> " + cards);

        request.setAttribute("cards", cards);

        LOG.trace("Set the request attribute: cards --> " + cards);

        int count = manager.countRequestAdmin();
        request.getSession().setAttribute("countAdmin", count);

        LOG.debug("Command finished");
        return Path.PAGE_REQUEST;
    }
}
