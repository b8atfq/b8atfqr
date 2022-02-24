package com.transfers.payments.web.command.common;

import org.apache.log4j.Logger;
import com.transfers.payments.Path;
import com.transfers.payments.web.command.base.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand extends Command {

    private static final long serialVersionUID = -2785976616686657267L;

    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        HttpSession session = request.getSession(false);
        if (session != null) {
            LOG.trace("invalidate session" + session);
            session.invalidate();
        }

        LOG.debug("Command finished");
        return Path.PAGE_LOGIN;
    }
}
