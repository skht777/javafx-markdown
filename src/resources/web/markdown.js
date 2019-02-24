window.onload = () => {
    alert("command:ready");
    marked.setOptions({
        langPrefix: ''
    });
};

window.mark = src => {
    document.getElementById('result').innerHTML = marked(src);
    Array.from(document.querySelectorAll('pre code')).forEach(block => {
        hljs.highlightBlock(block);
    });
    return src;
};
