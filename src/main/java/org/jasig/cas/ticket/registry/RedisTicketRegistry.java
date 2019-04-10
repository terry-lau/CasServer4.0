package org.jasig.cas.ticket.registry;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * CasServer集群环境下使用Redis缓存Ticket
 * @author Terry
 *
 */
public final class RedisTicketRegistry extends AbstractDistributedTicketRegistry implements DisposableBean {

    /**
     * redis client.
     */
    @NotNull
    private final RedisTemplate<String,Object> reidsTemplate;
    /**
     * TGT cache entry timeout in seconds.
     */
    @Min(0)
    private final int tgtTimeout;

    /**
     * ST cache entry timeout in seconds.
     */
    @Min(0)
    private final int stTimeout;

    /**
     * 初始化
     * @param redisUtil
     * @param ticketGrantingTicketTimeOut
     * @param serviceTicketTimeOut
     */
    public RedisTicketRegistry(final RedisTemplate<String,Object> reidsTemplate, int tgtTimeout,int stTimeout){
        this.reidsTemplate=reidsTemplate;
        this.tgtTimeout=tgtTimeout;
        this.stTimeout=stTimeout;
    }

    protected void updateTicket(final Ticket ticket) {
        logger.debug("Updating ticket {}", ticket);
        try {
        	 reidsTemplate.delete(ticket.getId());
             reidsTemplate.opsForValue().set(ticket.getId(),ticket, getTimeout(ticket), TimeUnit.SECONDS);
        } catch (final Exception e) {
            logger.error("Failed updating {}", ticket, e);
        }
    }

    public void addTicket(final Ticket ticket) {
        logger.debug("Adding ticket {}", ticket);
        try {
        	 reidsTemplate.opsForValue().set(ticket.getId(),ticket, getTimeout(ticket), TimeUnit.SECONDS);
        } catch (final Exception e) {
            logger.error("Failed adding {}", ticket, e);
        }
    }

    public boolean deleteTicket(final String ticketId) {
        logger.debug("Deleting ticket {}", ticketId);
        try {
        	reidsTemplate.delete(ticketId);
            return true;
        } catch (final Exception e) {
            logger.error("Failed deleting {}", ticketId, e);
        }
        return false;
    }

    public Ticket getTicket(final String ticketId) {
        try {
        	 final Ticket t = (Ticket) this.reidsTemplate.opsForValue().get(ticketId);
            if (t != null) {
                return getProxiedTicketInstance(t);
            }
        } catch (final Exception e) {
            logger.error("Failed fetching {} ", ticketId, e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * This operation is not supported.
     * @throws UnsupportedOperationException if you try and call this operation.
     */
    public Collection<Ticket> getTickets() {
    	throw new UnsupportedOperationException("GetTickets not supported.");
    }

    public void destroy() throws Exception {
    }

    @Override
    protected boolean needsCallback() {
        return true;
    }
    
    private int getTimeout(final Ticket t) {
        if (t instanceof TicketGrantingTicket) {
            return this.tgtTimeout;
        } else if (t instanceof ServiceTicket) {
            return this.stTimeout;
        }
        throw new IllegalArgumentException("Invalid ticket type");
    }
}