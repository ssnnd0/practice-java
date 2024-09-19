# practice-java
mep dont know how to do shizz, i got zaza!!!!!!!!!!!!

## Welcome to the Madness
Welcome to the most chaotic Java practice repo you'll ever see! Buckle up, because things are about to get wild.

### Random HTML
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crazy Page</title>
    <style>
        body {
            background-color: #ff00ff;
            color: #00ff00;
            font-family: 'Comic Sans MS', cursive, sans-serif;
            animation: rainbow 5s infinite;
        }
        @keyframes rainbow {
            0% {background-color: #ff0000;}
            25% {background-color: #00ff00;}
            50% {background-color: #0000ff;}
            75% {background-color: #ffff00;}
            100% {background-color: #ff00ff;}
        }
    </style>
    <script>
        document.body.addEventListener('mousemove', function(e) {
            const x = e.clientX;
            const y = e.clientY;
            const span = document.createElement('span');
            span.style.left = x + 'px';
            span.style.top = y + 'px';
            span.style.position = 'absolute';
            span.style.backgroundColor = 'rgba(255, 255, 255, 0.5)';
            span.style.borderRadius = '50%';
            span.style.width = '20px';
            span.style.height = '20px';
            document.body.appendChild(span);
            setTimeout(() => {
                span.remove();
            }, 500);
        });
    </script>
</head>

## Features

- no working ai
- can capture the king and continue playing in the old version
- help me pls


## Tech Stack

**Client:** Java

**Server:** Java


## Authors

- [@ssnnd0](https://www.github.com/ssnnd0)
![alt text](https://static.wikia.nocookie.net/fridaynightfunking/images/4/4a/CNightTankIdle.gif/revision/latest/thumbnail/width/360/height/360?cb=20220528180850)

