package com.transfers.payments.web.command.admin;

import org.apache.log4j.Logger;
import com.transfers.payments.Path;
import com.transfers.payments.db.DBManager;
import com.transfers.payments.db.entity.User;
import com.transfers.payments.exception.AppException;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListUsersCmd extends Command {

    private static final long serialVersionUID = 7732289478505L;

    private static final Logger LOG = Logger.getLogger(ListUsersCmd.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        // get users list list
        List<User> users = manager.findAllUsersClient();

        LOG.trace("Found in DB: usersList --> " + users);
        request.setAttribute("users", users);
        LOG.trace("Set the request attribute: users --> " + users);
        int count = manager.countRequestAdmin();
        request.getSession().setAttribute("countAdmin", count);
        LOG.debug("Command finished");
        return Path.PAGE_LIST_USERS;
    }
}
