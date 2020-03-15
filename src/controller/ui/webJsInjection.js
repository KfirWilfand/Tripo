Object.keys(window).forEach(key => {
let div = document.createElement('div');

                                    if (/^onmouseover/.test(key)) {
                                       window.addEventListener(key.slice(2), event => {
                                        console.log(event.fromElement);
                                        div.className = 'anotherClass';
                                       div.style.position = 'absolute';
                                        div.style.content = '';
                                        div.style.height = `${event.fromElement.offsetHeight +'px'}`;
                                        div.style.width = `${event.fromElement.offsetWidth +'px'}`;
                                        div.style.top = `${event.fromElement.offsetTop + 'px'}`;
//                                "        div.style.right = `${100+ 'px'}`;\n" +
//                                "        div.style.bottom = `${100 + 'px'}`;\n" +
                                        div.style.left = `${event.fromElement.offsetLeft + 'px'}`;
                                       div.style.background = '#05f';
                                        div.style.opacity = '0.25';

                                        event.fromElement.appendChild(div);
                                        });
                                    if (/^onmouseout/.test(key)) {
                                        window.addEventListener(key.slice(2), event => {
                                        div.onmouseout='{div.style.display === \"none\"}';
                                        });
                                    }
                                    }
                                });

