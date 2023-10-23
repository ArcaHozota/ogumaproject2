$(function() {
	$(".list-group-item").on('click', function() {
		if ($(this).find("ul")) {
			$(this).toggleClass("tree-closed");
			if ($(this).hasClass("tree-closed")) {
				$("ul", this).hide("fast");
			} else {
				$("ul", this).show("fast");
			}
		}
	});
	$("#logout-link").on('click', function(e) {
		e.preventDefault();
		$("#logout-form").submit();
	});
	$("#search-button").on('click', function(e) {
		let searchInput = $("#search-input").val();
	});
	$("#to-mainmenu").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		$.ajax({
			url: '/pgcrowd/employee/to/mainmenu',
			data: 'username=' + username,
			type: 'GET',
			dataType: 'html'
		});
	});
	$("#toAdmin").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		$.ajax({
			url: '/pgcrowd/employee/to/pages',
			data: 'username=' + username,
			type: 'GET',
			dataType: 'html'
		});
	});
});