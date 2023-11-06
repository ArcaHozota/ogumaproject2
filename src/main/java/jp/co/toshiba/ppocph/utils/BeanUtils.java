package jp.co.toshiba.ppocph.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 共通Beanツール
 *
 * @author ArkamaHozota
 * @since 4.31
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BeanUtils extends org.springframework.beans.BeanUtils {

	/**
	 * NULLではないプロパティだけコピーする
	 *
	 * @param source コピー元
	 * @param target コピー先
	 * @throws BeansException
	 */
	public static void copyNullableProperties(final Object source, final Object target) throws BeansException {
		org.springframework.beans.BeanUtils.copyProperties(source, target, BeanUtils.getNullProperties(source));
	}

	/**
	 * コピー元のNULLプロパティを取得する
	 *
	 * @param source コピー元
	 * @return NULLプロパティ
	 */
	private static String[] getNullProperties(final Object source) {
		final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
		final PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
		final Set<String> nullFields = new HashSet<>();
		if (propertyDescriptors.length > 0) {
			for (final PropertyDescriptor p : propertyDescriptors) {
				final String name = p.getName();
				final Object value = beanWrapper.getPropertyValue(name);
				if (Objects.isNull(value)) {
					nullFields.add(name);
				}
			}
		}
		return nullFields.toArray(new String[nullFields.size()]);
	}
}
