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

public class AcceptReqCmd extends Command {

    private static final long serialVersionUID = 77288888755558505L;

    private static final Logger LOG = Logger.getLogger(UnblockCardCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        String id = request.getParameter("card_id");
        Card card = manager.findCard(Integer.parseInt(id));
        LOG.trace("Found in DB: card --> " + card);
        card.setActivityId(0);
        card.setRequestId(0);
        try {
            LOG.trace("update in DB: card --> " + card);
            manager.updateCard(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int count = manager.countRequestAdmin();
        request.getSession().setAttribute("countAdmin", count);
        LOG.debug("Command finished");
        return Path.COMMAND_REQUEST;
    }
}
