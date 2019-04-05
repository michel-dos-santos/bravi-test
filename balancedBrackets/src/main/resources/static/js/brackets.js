
function bracketsOnly(evt) {
	key = evt.key
	keyCode = evt.keyCode
	return key && 
			//allow brackets ( ) { } [ ]
			(["(", ")", "{", "}", "[", "]"].includes(key) ||
			//allow backspace, delete, tab, enter, home, end and arrows <- ->
			[8, 46, 9, 13, 36, 35, 37, 39].includes(keyCode))
}