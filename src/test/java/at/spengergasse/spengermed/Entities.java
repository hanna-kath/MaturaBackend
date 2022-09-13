package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Element;
import java.beans.PropertyDescriptor;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class Entities {
  @SneakyThrows
  public static void unsetAllIds(Object o) {
    if (o == null) {
      return;
    }
    if (o.getClass().getName().startsWith("java.lang.")) {
      return;
    }
    if (o instanceof Iterable<?> i) {
      for (Object e : i) {
        unsetAllIds(e);
      }
      return;
    }
    if (o instanceof Element e) {
      e.setId(null);
    }
    for (PropertyDescriptor p : BeanUtils.getPropertyDescriptors(o.getClass())) {
      unsetAllIds(p.getReadMethod().invoke(o));
    }
  }
}
