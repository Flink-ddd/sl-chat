package sl.chat.dev.server.controller;

import sl.chat.dev.common.core.model.PrivateMessageInfo;
import sl.chat.dev.common.core.utils.Result;
import sl.chat.dev.common.core.utils.ResultUtils;
import sl.chat.dev.common.log.annotation.ApiLog;
import sl.chat.dev.server.aop.annotation.AnonymousUserCheck;
import sl.chat.dev.server.common.vo.message.MessageSendResp;
import sl.chat.dev.server.common.vo.message.PrivateMessageSendReq;
import sl.chat.dev.server.service.IPrivateMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AnonymousUserCheck
@Api(tags = "私聊消息")
@RestController
@RequestMapping("/message/private")
public class PrivateMessageController {

  @Autowired
  private IPrivateMessageService privateMessageService;

  @ApiLog
  @PostMapping("/send")
  @ApiOperation(value = "发送消息", notes = "发送私聊消息")
  public Result<MessageSendResp> sendMessage(@Valid @RequestBody PrivateMessageSendReq vo) {
    return ResultUtils.success(privateMessageService.sendMessage(vo));
  }

  @DeleteMapping("/recall/{id}")
  @ApiOperation(value = "撤回消息", notes = "撤回私聊消息")
  public Result<Long> recallMessage(@NotNull(message = "消息id不能为空") @PathVariable Long id) {
    privateMessageService.recallMessage(id);
    return ResultUtils.success();
  }

  @PostMapping("/pullUnreadMessage")
  @ApiOperation(value = "拉取未读消息", notes = "拉取未读消息")
  public Result pullUnreadMessage() {
    privateMessageService.pullUnreadMessage();
    return ResultUtils.success();
  }

  @GetMapping("/history")
  @ApiOperation(value = "查询聊天记录", notes = "查询聊天记录")
  public Result<List<PrivateMessageInfo>> recallMessage(
      @NotNull(message = "好友id不能为空") @RequestParam Long friendId,
      @RequestParam(required = false) Long lastMessageId) {
    return ResultUtils.success(privateMessageService.findHistoryMessage(friendId, lastMessageId));
  }

}
