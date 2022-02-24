package com.transfers.payments.web.command.client;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
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

public class UserBlockCardCmd extends Command {

    private static final long serialVersionUID = 7723435677478505L;

    private static final Logger LOG = Logger.getLogger(UserBlockCardCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        User user = (User) request.getSession().getAttribute("user");
        LOG.trace("get attribute from session" + user);
        if (!user.getPassword().equals(DigestUtils.md5Hex(request.getParameter("password")))) {
            LOG.debug("password does not match");
            throw new AppException(Messages.YOUR_PASSWORD_DOES_NOT_MATCH);
        }
        DBManager manager = DBManager.getInstance();

        String id =  request.getParameter("card_id");
        LOG.trace("get parameter" + id);
        Card card = manager.findCard(Long.parseLong(id));
        LOG.trace("find card in db" + card);
        card.setActivityId(1);
        try {
            manager.updateCard(card);
            LOG.debug("update card in db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOG.debug("Command finished");
        return Path.COMMAND_USER_CARDS;
    }
}
