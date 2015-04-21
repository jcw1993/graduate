
/*validate date format*/
function validateDateFormat(dateStr) {
	var datePattern = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/;
	return datePattern.test(dateStr);
}

/*validate time format*/
function validateTimeFormat(timeStr) {
	var timePattern = /^(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
	return timePattern.test(timeStr);
}

/*check is number*/
function isNumber(numberStr) {
	var numberPattern = /^\d+$/;
	if(numberPattern.test(numberStr)) {
		return true;
	}
	return false;
}

function checkLength(str, minLength, maxLength) {
	if(!str) {
		return false;
	}
	str = str.trim();
	if(str.length < minLength || str.length > maxLength) {
		return false;
	}

	return true;
}