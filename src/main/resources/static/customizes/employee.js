$(function() {
	$(".list-group-item").on('click', function() {
		if ($(this).find("ul")) {
			$(this).toggleClass("collapsed");
			if ($(this).hasClass("collapsed")) {
				$("ul", this).hide('fast');
			} else {
				$("ul", this).show('fast');
			}
		}
	});
	$("#logoutLink").on('click', function(e) {
		e.preventDefault();
		$("#logoutForm").submit();
	});
	$("#toMainmenu").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		window.location.replace('/pgcrowd/employee/to/mainmenu?username=' + username);
	});
	$("#toMainmenu2").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		window.location.replace('/pgcrowd/employee/to/mainmenu?username=' + username);
	});
	$("#toAdmin").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		window.location.replace('/pgcrowd/employee/to/pages?username=' + username);
	});
	$("#toPages").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		window.location.replace('/pgcrowd/employee/to/pages?username=' + username);
	});
});