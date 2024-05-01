$(document).ready(function() {
	$("#toMainmenu").css('color', '#7F0020');
});
$("#categoryKanriMainmenu").on('click', function() {
	let url = '/oguma/category/initial';
	checkPermissionAndTransfer(url);
});
$("#roleKanriMainmenu").on('click', function() {
	let url = '/oguma/role/toPages?pageNum=1';
	checkPermissionAndTransfer(url);
});
$("#adminKanriMainmenu").on('click', function() {
	let url = '/oguma/employee/toPages?pageNum=1';
	checkPermissionAndTransfer(url);
});