package com.project.cdio.services;

import com.project.cdio.entities.ReplyEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.dto.ReplyDTO;

public interface IReplyService {
    ReplyEntity createReply(ReplyDTO replyDTO) throws DataNotFoundException;
}
