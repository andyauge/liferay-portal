/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.ScreenNameValidator;

/**
 * @author Brian Wing Shun Chan
 */
public class UserScreenNameException extends PortalException {

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserScreenNameException() {
		super();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserScreenNameException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserScreenNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserScreenNameException(Throwable cause) {
		super(cause);
	}

	public static class MustBeAlphaNumeric extends UserScreenNameException {

		public MustBeAlphaNumeric(
			long userId, String screenName, char ... validSpecialChars) {

			super(
				String.format(
					"Screen name %s for user %s must be alphanumeric or one " +
						"of the following special characters: %s",
					screenName, userId, new String(validSpecialChars)));

			_userId = userId;
			_screenName = screenName;
			_validSpecialChars = validSpecialChars;
		}

		public String getScreenName() {
			return _screenName;
		}

		public long getUserId() {
			return _userId;
		}

		public char[] getValidSpecialChars() {
			return _validSpecialChars;
		}

		private String _screenName;
		private long _userId;
		private char[] _validSpecialChars;

	}

	public static class MustNotBeDuplicate extends UserScreenNameException {

		public MustNotBeDuplicate(long userId, String screenName) {
			super(
				String.format(
					"Screen name %s must not be duplicate but is already " +
						"used by user %s",
					screenName, userId));

			_userId = userId;
			_screenName = screenName;
		}

		public String getScreenName() {
			return _screenName;
		}

		public long getUserId() {
			return _userId;
		}

		private String _screenName;
		private long _userId;

	}

	public static class MustNotBeNull extends UserScreenNameException {

		public MustNotBeNull() {
			super("Screen name must not be null");
		}

		public MustNotBeNull(long userId) {
			super(
				String.format(
					"Screen name must not be null for user %s", userId));
		}

		public MustNotBeNull(String fullName) {
			super(
				String.format(
					"Screen name must not be null for the full name %s",
					fullName));
		}

	}

	public static class MustNotBeNumeric extends UserScreenNameException {

		public MustNotBeNumeric(long userId, String screenName) {
			super(
				String.format(
					"Screen name %s for user %s must not be numeric because " +
						"the portal property \"%s\" is disabled",
					screenName, userId,
					PropsKeys.USERS_SCREEN_NAME_ALLOW_NUMERIC));

			_userId = userId;
			_screenName = screenName;
		}

		public String getScreenName() {
			return _screenName;
		}

		public long getUserId() {
			return _userId;
		}

		private String _screenName;
		private long _userId;

	}

	public static class MustNotBeReservedForAnonymous
		extends UserScreenNameException {

		public MustNotBeReservedForAnonymous(
			long userId, String screenName, String[] reservedScreenNames) {

			super(
				String.format(
					"Screen name %s for user %s must not be a reserved name " +
						"for anonymous users such as: %s",
					screenName, userId, StringUtil.merge(reservedScreenNames)));

			_userId = userId;
			_screenName = screenName;
			_reservedScreenNames = reservedScreenNames;
		}

		public String[] getReservedScreenNames() {
			return _reservedScreenNames;
		}

		public String getScreenName() {
			return _screenName;
		}

		public long getUserId() {
			return _userId;
		}

		private String[] _reservedScreenNames;
		private String _screenName;
		private long _userId;

	}

	public static class MustNotBeUsedByGroup extends UserScreenNameException {

		public MustNotBeUsedByGroup(
			long userId, String screenName, Group group) {

			super(
				String.format(
					"Screen name %s for user %s must not be used by group %s",
					screenName, userId, group.getGroupId()));

			_userId = userId;
			_screenName = screenName;
			_group = group;
		}

		public Group getGroup() {
			return _group;
		}

		public String getScreenName() {
			return _screenName;
		}

		public long getUserId() {
			return _userId;
		}

		private Group _group;
		private String _screenName;
		private long _userId;

	}

	public static class MustProduceValidFriendlyURL
		extends UserScreenNameException {

		public MustProduceValidFriendlyURL(
			long userId, String screenName, int exceptionType) {

			super(
				String.format(
					"Screen name %s for user %s must produce a valid " +
						"friendly URL",
					screenName, userId),
				new GroupFriendlyURLException(exceptionType));

			_userId = userId;
			_screenName = screenName;
			_exceptionType = exceptionType;
		}

		public int getExceptionType() {
			return _exceptionType;
		}

		public String getScreenName() {
			return _screenName;
		}

		public long getUserId() {
			return _userId;
		}

		private int _exceptionType;
		private String _screenName;
		private long _userId;

	}

	public static class MustValidate extends UserScreenNameException {

		public MustValidate(
			long userId, String screenName,
			ScreenNameValidator screenNameValidator) {

			super(
				String.format(
					"Screen name %s for user %s must validate with %s",
					screenName, userId,
					ClassUtil.getClassName(screenNameValidator)));

			_userId = userId;
			_screenName = screenName;
			_screenNameValidator = screenNameValidator;
		}

		public String getScreenName() {
			return _screenName;
		}

		public ScreenNameValidator getScreenNameValidator() {
			return _screenNameValidator;
		}

		public long getUserId() {
			return _userId;
		}

		private String _screenName;
		private ScreenNameValidator _screenNameValidator;
		private long _userId;

	}

}