package sl.chat.dev.server.controller;

import sl.chat.dev.common.core.utils.Result;
import sl.chat.dev.common.core.utils.ResultUtils;
import sl.chat.dev.server.aop.annotation.AnonymousUserCheck;
import sl.chat.dev.server.common.entity.Friend;
import sl.chat.dev.server.common.vo.user.FriendVO;
import sl.chat.dev.server.service.IFriendService;
import sl.chat.dev.server.util.SessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "好友")
@RestController
@RequestMapping("/friend")
public class FriendController {

  @Autowired
  private IFriendService friendService;

  @GetMapping("/list")
  @ApiOperation(value = "好友列表", notes = "获取好友列表")
  public Result<List<FriendVO>> findFriends() {
    List<Friend> friends = friendService.findFriendByUserId(SessionContext.getSession().getId());
    List<FriendVO> vos = friends.stream().map(f -> {
      FriendVO vo = new FriendVO();
      vo.setId(f.getFriendId());
      vo.setHeadImage(f.getFriendHeadImage());
      vo.setNickName(f.getFriendNickName());
      return vo;
    }).collect(Collectors.toList());
    return ResultUtils.success(vos);
  }

  @PostMapping("/add")
  @AnonymousUserCheck
  @ApiOperation(value = "添加好友", notes = "双方建立好友关系")
  public Result addFriend(
      @NotEmpty(message = "好友id不可为空") @RequestParam("friendId") Long friendId) {
    friendService.addFriend(friendId);
    return ResultUtils.success();
  }

  @GetMapping("/find/{friendId}")
  @AnonymousUserCheck
  @ApiOperation(value = "查找好友信息", notes = "查找好友信息")
  public Result<FriendVO> findFriend(
      @NotEmpty(message = "好友id不可为空") @PathVariable("friendId") Long friendId) {
    return ResultUtils.success(friendService.findFriend(friendId));
  }

  @GetMapping("/findFriendApply/{friendId}")
  @AnonymousUserCheck
  @ApiOperation(value = "查询新好友申请", notes = "查询新好友申请")
  public Result<List<FriendVO>> findFriendApply(
          @NotEmpty(message = "好友id不可为空") @PathVariable("friendId") Long friendId) {
    return ResultUtils.success(friendService.findFriendApply(friendId));
  }

  @PostMapping("/verifyFriends")
  @AnonymousUserCheck
  @ApiOperation(value = "验证好友", notes = "验证好友")
  public Result verifyFriends(
          @NotEmpty(message = "好友id不可为空") @RequestParam("friendId") Long friendId,
          @NotEmpty(message = "1：通过、2：拒绝") @RequestParam("audit") Integer audit) {
    friendService.verifyFriends(friendId,audit);
    return ResultUtils.success();
  }

  @AnonymousUserCheck
  @DeleteMapping("/delete/{friendId}")
  @ApiOperation(value = "删除好友", notes = "解除好友关系")
  public Result delFriend(
      @NotEmpty(message = "好友id不可为空") @PathVariable("friendId") Long friendId) {
    friendService.delFriend(friendId);
    return ResultUtils.success();
  }

  @AnonymousUserCheck
  @PutMapping("/update")
  @ApiOperation(value = "更新好友信息", notes = "更新好友头像或昵称")
  public Result modifyFriend(@Valid @RequestBody FriendVO vo) {
    friendService.update(vo);
    return ResultUtils.success();
  }



}
