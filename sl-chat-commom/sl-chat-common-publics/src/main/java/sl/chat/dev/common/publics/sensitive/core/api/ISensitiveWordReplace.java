package sl.chat.dev.common.publics.sensitive.core.api;

/**
 * 敏感词替换策略 muxiaohui
 */
public interface ISensitiveWordReplace {

  /**
   * 替换
   *
   * @param context 上下文
   * @return 结果
   */
  String replace(ISensitiveWordReplaceContext context);

}
