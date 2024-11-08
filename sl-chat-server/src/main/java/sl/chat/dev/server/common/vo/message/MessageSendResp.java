package sl.chat.dev.server.common.vo.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author muxiaohui
 * @date 2023-07-09 22:25
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageSendResp implements Serializable {

  private Long id;

  private String content;

}
