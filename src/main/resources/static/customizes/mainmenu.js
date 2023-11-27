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
		let userId = $("#userinfoId").text();
		window.location.replace('/pgcrowd/employee/to/mainmenu?userId=' + userId);
	});
	$("#toMainmenu2").on('click', function(e) {
		e.preventDefault();
		let userId = $("#userinfoId").text();
		window.location.replace('/pgcrowd/employee/to/mainmenu?userId=' + userId);
	});
	$("#toAdmin").on('click', function(e) {
		e.preventDefault();
		let userId = $("#userinfoId").text();
		window.location.replace('/pgcrowd/employee/to/pages?pageNum=1&userId=' + userId);
	});
	$("#toPages").on('click', function(e) {
		e.preventDefault();
		let userId = $("#userinfoId").text();
		window.location.replace('/pgcrowd/employee/to/pages?pageNum=1&userId=' + userId);
	});
	$("#addInfoBtn").on('click', function(e) {
		e.preventDefault();
		let userId = $("#userinfoId").text();
		window.location.replace('/pgcrowd/employee/to/addition?userId=' + userId);
	});
});