package com.is.evaluation.exception;

import java.util.Map;
import java.util.TreeMap;

public class Message {
    private final static Map<MessageDescription, ExceptionResponse> vMessageTypes = new TreeMap<MessageDescription, ExceptionResponse>(){{
        put(MessageDescription.stateNullOrEmpty, new ExceptionResponse("1","The queried state cannot be empty or null.", "detalle -_-"));
        put(MessageDescription.stateNotValid, new ExceptionResponse("2","The queried status % s is not allowed.", "detalle -_-"));
        put(MessageDescription.repeated, new ExceptionResponse("3","There is a record with the %s: %s", "detalle -_-"));
        put(MessageDescription.notExists, new ExceptionResponse("4","%s with %s %s not found", "detalle -_-"));
        put(MessageDescription.UserWithoutRoles, new ExceptionResponse("5","Login error: user does not have assigned roles", "detalle -_-"));
        put(MessageDescription.UsernameNotFound, new ExceptionResponse("6","The username %s is not registered.", "detalle -_-"));
        put(MessageDescription.RepeatedCourse, new ExceptionResponse("7","User [%s] is already registered to the course %s.", "detalle -_-"));

    }};

    public static BadRequestException GetBadRequest(MessageDescription pMessageDescription, Object[] pArgs)
    {
        ExceptionResponse vMessageDetail = vMessageTypes.get(pMessageDescription);
        String vNewMessage = vMessageDetail.getMessage();
        vNewMessage = String.format(vNewMessage, pArgs);
        return new BadRequestException(vMessageDetail.getCode(), vNewMessage);
    }
    public static NotFoundException GetNotFound(MessageDescription pMessageDescription, Object[] pArgs)
    {
        ExceptionResponse vMessageDetail = vMessageTypes.get(pMessageDescription);
        String vNewMessage = vMessageDetail.getMessage();
        vNewMessage = String.format(vNewMessage, pArgs);
        return new NotFoundException(vMessageDetail.getCode(), vNewMessage);
    }

}
