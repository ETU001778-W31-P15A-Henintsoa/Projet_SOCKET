<?php
if(!isset($_SERVER['HTTP_POST'])){
    parse_str($argv[1],$_GET);
    parse_str($argv[1],$_POST);
}
?>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bienvenue</title>
    </head>

    <body>
        <h2> Bonjour <?php echo $_POST['name'] ; ?></h2>
    </body>

    </html>
