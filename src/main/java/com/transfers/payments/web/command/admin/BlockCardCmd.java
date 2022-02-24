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
import java.sql.SQLException;

public class BlockCardCmd extends Command {

    private static final long serialVersionUID = 7723435677478505L;

    private static final Logger LOG = Logger.getLogger(BlockCardCmd.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        String id = request.getParameter("card_id");
        Card card = manager.findCard(Integer.parseInt(id));
        LOG.trace("Found in DB: card --> " + card);
        card.setActivityId(1);
        try {
            manager.updateCard(card);
            LOG.trace("update in DB: card --> " + card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOG.debug("Command finished");
        return Path.COMMAND_LIST_CARDS + "&user_id=" + manager.findUserByCardId(Integer.parseInt(id)).getId();
    }
}
