package com.transfers.payments.web.command.admin;

import org.apache.log4j.Logger;
import com.transfers.payments.Path;
import com.transfers.payments.db.DBManager;
import com.transfers.payments.db.entity.Card;
import com.transfers.payments.exception.AppException;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class UnblockCardCmd extends Command {

    private static final long serialVersionUID = 7728888877478505L;

    private static final Logger LOG = Logger.getLogger(UnblockCardCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        String id = request.getParameter("card_id");
        Card card = manager.findCard(Integer.parseInt(id));
        LOG.trace("Found in DB: card --> " + card);
        card.setActivityId(0);
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
