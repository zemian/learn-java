https://developer.mozilla.org/en-US/docs/Web/API/Element/innerHTML

TML5 specifies that a <script> tag inserted with innerHTML should not execute.

However, there are ways to execute JavaScript without using <script> elements,
so there is still a security risk whenever you use innerHTML to set strings
over which you have no control. For example:

    const name = "<img src='x' onerror='alert(1)'>";
    el.innerHTML = name; // shows the alert
