<html>
<head>
<script type="text/javascript" src="qrcode.js"></script>
</head>
<script type="text/javascript">
	function generaQR() {
		var form = document.forms['qrForm'];
		var typeNumber = 4;
		var errorCorrectionLevel = form.elements['e'].value;
		var qr = qrcode(typeNumber, errorCorrectionLevel);
		qr.addData(document.getElementById('msg').value);
		qr.make();
		
		document.getElementById('qr').innerHTML = qr.createImgTag(5, 20);
	}
</script>
<body>
<form name="qrForm">
	<h2>Prueba codigo QR</h2>
	<div>
		ErrorCorrectionLevel: 
		<select id="e" name="e">
		        <option value="L">L(7%)</option>
		        <option value="M" selected="selected">M(15%)</option>
		        <option value="Q">Q(25%)</option>
		        <option value="H">H(30%)</option>
		</select>
	</div>
	<div>
		<textarea id="msg" name="msg" rows="10" cols="40">texto</textarea>
	</div>
	<input type="button" value="Generar QR" onclick="generaQR()"/>
	<div id="qr"></div>
</form>
</body>
</html>
