package zhongxin.m365.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Text;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 输入控制类
 * 
 * @author zhaohuathinkpad 判断输入是否为空 判断输入是否合法
 */
public class InputControl {
	/**
	 * 判断控件 tetx是否为空 如果为空 为false 否则true
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isEmpty(EditText editText) {
		if (editText.getText() == null
				|| editText.getText().toString().equals("")
				|| editText.getText().toString() == null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断控件 tetx是否为空 如果为空 为false 否则true
	 * 
	 * @param textview
	 * @return
	 */
	public static boolean isEmpty(TextView textview) {
		if (textview.getText() == null
				|| textview.getText().toString().equals("")
				|| textview.getText().toString() == null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断控件 tetx是否为空 如果为空 为false 否则true
	 * 
	 * @param textview
	 * @return
	 */
	public static boolean isEmpty(Button button) {
		if (button.getText() == null || button.getText().toString().equals("")
				|| button.getText().toString() == null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断是否是 固话号码 或手机号码
	 */
	public static boolean isPhoneNumberValid(EditText editText) {
		if (editText.getText() == null) {
			return false;
		} else {
			String number = editText.getText().toString();
			// String phone = "0\\d{2,3}-\\d{7,8}";
			String phone = "0\\d{2,3}\\d{7,8}";

			Pattern p = Pattern.compile(phone);
			Matcher m = p.matcher(number);
			return m.matches();

		}
	}

	/**
	 * 判断是否是 固话号码 或手机号码
	 */
	public static boolean isPhoneNumberValid(TextView textView) {
		if (textView.getText() == null) {
			return false;
		} else {

			String number = textView.getText().toString();
			// String phone = "0\\d{2,3}-\\d{7,8}";
			String phone = "0\\d{2,3}\\d{7,8}";
			Pattern p = Pattern.compile(phone);
			Matcher m = p.matcher(number);
			return m.matches();

		}
	}

	/**
	 * 判断是否是手机号
	 * 
	 * @param textview
	 * @return
	 */
	public static boolean isPhoneNumber(TextView textview) {
		if (textview.getText() != null
				&& textview.getText().toString().length() == 11) {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[6-8]))\\d{8}$");
			if (textview.getText().toString() != null) {
				Matcher m = p.matcher(textview.getText().toString());
				return m.matches(); /* 合法手机号 */

			} else {

				return false;/* 手机号段错误，或字符不是数字 */
			}
		} else {

			return false; /* 手机号为空或 手机号位数错误 */
		}

	}

	/**
	 * 字符串仅包含 大写字母 数字 字符串长度是否为5
	 */
	public static boolean ISCarNumber(String str) {
		if (str != null && str.length() == 5) {
			Pattern p = Pattern.compile("[A-Z0-9]+");
			Matcher m = p.matcher(str);
			System.out.println(m.matches());
			return !m.matches();
		} else {
			return false;
		}
	}

	/**
	 * 字符串仅包含 数字 字符串长度是否为5
	 */
	public static boolean ISNumber(TextView textView) {
		if (textView.getText() != null) {
			Pattern p = Pattern.compile("[0-9]+");
			Matcher m = p.matcher(textView.getText().toString());
			System.out.println(m.matches());
			return !m.matches();
		} else {
			return false;
		}
	}

	public static boolean ISNumber(EditText editText) {
		if (editText.getText() != null) {
			Pattern p = Pattern.compile("[0-9]+");
			Matcher m = p.matcher(editText.getText().toString());
			System.out.println(m.matches());
			return !m.matches();
		} else {
			return false;
		}

	}

	/**
	 * 仅包含大写字母 和数字
	 */
	// 判断字符串中是否仅包含字母数字和汉字
	// *各种字符的unicode编码的范围：
	// * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
	// * 数字：[0x30,0x39]（或十进制[48, 57]）
	// *小写字母：[0x61,0x7a]（或十进制[97, 122]）
	// * 大写字母：[0x41,0x5a]（或十进制[65, 90]）

	public static boolean IsLetterAndNUms(String str) {
		String regex = "[0-9A-Z]";
		return str.matches(regex);

	}

	public static boolean isLetterDigitOrNUms(String str) {
		// String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
		String regex = "^[0-9A-Z]+$";

		return str.matches(regex);
	}

}
