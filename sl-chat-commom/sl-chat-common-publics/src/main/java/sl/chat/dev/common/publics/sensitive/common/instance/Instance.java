package sl.chat.dev.common.publics.sensitive.common.instance;

/**
 * @author muxiaohui
 */
public interface Instance {

  <T> T singleton(Class<T> var1, String var2);

  <T> T singleton(Class<T> var1);

  <T> T threadLocal(Class<T> var1);

  <T> T multiple(Class<T> var1);

  <T> T threadSafe(Class<T> var1);
}
