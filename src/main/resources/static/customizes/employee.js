$(function() {
	$("#logoutLink").on('click', function(e) {
		e.preventDefault();
		$("#logoutForm").submit();
	});
	$("#toMainmenu").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		window.location.replace('/pgcrowd/employee/to/mainmenu?username=' + username);
	});
	$("#toAdmin").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		window.location.replace('/pgcrowd/employee/to/pages?username=' + username);
	});
});