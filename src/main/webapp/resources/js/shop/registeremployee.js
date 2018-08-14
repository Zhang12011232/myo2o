$(function() {
	var registerUrl = '/myo2o/shop/addshopauthmap';
	$('#submit').click(function() {
		var shopAuthMap = {};
		var personInfo = {};
		shopAuthMap.name = $('#userName').val();
		shopAuthMap.title = $('#title').val();
		personInfo.name = $('#userName').val();
		personInfo.phone = $('#phone').val();
		personInfo.email = $('#email').val();
		shopAuthMap.employee = personInfo;
		var thumbnail = $('#small-img')[0].files[0];
		console.log(thumbnail);
		var formData = new FormData();
		formData.append('thumbnail', thumbnail);
		formData.append('shopAuthMapStr', JSON.stringify(shopAuthMap));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : registerUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					window.location.href = '/myo2o/shop/shopauthmanage';
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/myo2o/shop/shopauthmanage';
	});
});
