package com.transfers.payments.web.command.client;


import org.apache.log4j.Logger;
import com.transfers.payments.CardNumber;
import com.transfers.payments.Path;
import com.transfers.payments.db.DBManager;
import com.transfers.payments.db.entity.Card;
import com.transfers.payments.db.entity.User;
import com.transfers.payments.exception.AppException;
import com.transfers.payments.exception.Messages;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CreateCardCmd extends Command {

    private static final long serialVersionUID = 3088745587456473L;

    private static final Logger LOG = Logger.getLogger(CreateCardCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        String name = request.getParameter("name");
        String link = (String) request.getSession().getAttribute("name_link");
        if (name.equals(link)) {
            return Path.COMMAND_USER_CARDS;
        }
        LOG.trace("get from request parameter" + name);
        if (name == null || name.isEmpty()) {
            LOG.trace("fields was empty");
            throw new AppException(Messages.FIELDS_CANNOT_BE_EMPTY);
        }
        DBManager manager = DBManager.getInstance();
        User user = (User) request.getSession().getAttribute("user");
        LOG.trace("get attribute from session" + user);
        List<Card> cards = manager.getUserCards(user);
        LOG.trace("Found in DB: cardsList --> " + cards);
        for (Card card : cards) {
            if (name.equals(card.getName())) {
                throw new AppException(Messages.CARD_WITH_THIS_NAME_ALREADY_EXISTS);
            }
        }
        Card card = new Card();
        card.setName(name);
        card.setUserId(user.getId());
        card.setNumber(CardNumber.createCardNumber());
        try {
            LOG.trace("Set in DB: card --> " + card);
            manager.insertCard(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("name_link", name);
        LOG.debug("Command finished");
        return Path.COMMAND_USER_CARDS;

    }
}
