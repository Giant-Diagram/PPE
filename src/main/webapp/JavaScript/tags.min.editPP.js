const ACTIVE_CLASS = "bg-light", VALUE_ATTRIBUTE = "data-value";
var counter = 0;
var firstStart = true;

class Tags {
    constructor(e) {
        this.selectElement = e, this.selectElement.style.display = "none", this.placeholder = this.getPlaceholder(), this.allowNew = !!e.dataset.allowNew, this.holderElement = document.createElement("div"), this.containerElement = document.createElement("div"), this.dropElement = document.createElement("ul"), this.searchInput = document.createElement("input"), this.holderElement.classList.add("tags"), this.holderElement.appendChild(this.containerElement), this.containerElement.appendChild(this.searchInput), this.holderElement.appendChild(this.dropElement), this.selectElement.parentNode.insertBefore(this.holderElement, this.selectElement.nextSibling), this.configureSearchInput(), this.configureHolderElement(), this.configureDropElement(), this.configureContainerElement()
    }

    static init(e = "select[multiple]") {
        let t = document.querySelectorAll(e);
        for (let e = 0; e < t.length; e++) {
            let s = t[e];
            new Tags(s)
        }
    }

    getPlaceholder() {
        let e = this.selectElement.querySelector("option");
        if (e) {
            if (!e.value) {
                let t = e.innerText;
                return e.remove(), t
            }
            return this.selectElement.getAttribute("placeholder") ? this.selectElement.getAttribute("placeholder") : this.selectElement.getAttribute("data-placeholder") ? this.selectElement.getAttribute("data-placeholder") : ""
        }
    }

    configureDropElement() {
        this.dropElement.classList.add("dropdown-menu")
    }

    configureHolderElement() {
        let type = this.selectElement.parentElement.lastElementChild.innerText.replace(' *','');

        this.holderElement.classList.add("form-control"), this.holderElement.classList.add("dropdown"), this.holderElement.classList.add("addPractice"), this.holderElement.setAttribute("title","Eingabe wird durch \"Enter\" getrennt")

        if (type.toUpperCase() === "TECHNOLOGIEN"){
            this.holderElement.id = "TECHNOLOGIEN";
        }else{
            this.holderElement.id = "ANFORDERUNGEN";
        }

        tooltipTagId(this.holderElement.getAttribute("id"));
    }

    configureContainerElement() {
        this.containerElement.addEventListener("click", e => {
            this.searchInput.focus()
        });
        let e = this.selectElement.querySelectorAll("option[selected]");
        for (let t = 0; t < e.length; t++) {
            let s = e[t];
            s.value && this.addItem(s.innerText, s.value)
        }
        if (counter >= 1){
            firstStart = false;
        }else{
            counter++;
        }
    }

    configureSearchInput() {
        this.searchInput.type = "text", this.searchInput.autocomplete = !1, this.searchInput.style.border = 0, this.searchInput.style.outline = 0, this.searchInput.style.maxWidth = "100%", this.adjustWidth(), this.searchInput.addEventListener("input", e => {
            this.adjustWidth(), this.searchInput.value.length >= 1 ? this.showSuggestions() : this.hideSuggestions()
        }), this.searchInput.addEventListener("keydown", e => {
            if ("Enter" == e.code) {
                let t = this.getActiveSelection();
                return t ? (this.addItem(t.innerText, t.getAttribute(VALUE_ATTRIBUTE)), this.resetSearchInput(), this.hideSuggestions()) : this.allowNew && (this.addItem(this.searchInput.value), this.resetSearchInput(), this.hideSuggestions()), void e.preventDefault()
            }
            "ArrowUp" == e.code && this.moveSelectionUp(), "ArrowDown" == e.code && this.moveSelectionDown(), "Backspace" == e.code && 0 == this.searchInput.value.length && (this.removeLastItem(), this.adjustWidth())
        })
    }

    moveSelectionUp() {
        let e = this.getActiveSelection();
        if (e) {
            let t = e.parentNode;
            do {
                t = t.previousSibling
            } while (t && "none" == t.style.display);
            if (!t) return;
            e.classList.remove(ACTIVE_CLASS), t.querySelector("a").classList.add(ACTIVE_CLASS)
        }
    }

    moveSelectionDown() {
        let e = this.getActiveSelection();
        if (e) {
            let t = e.parentNode;
            do {
                t = t.nextSibling
            } while (t && "none" == t.style.display);
            if (!t) return;
            e.classList.remove(ACTIVE_CLASS), t.querySelector("a").classList.add(ACTIVE_CLASS)
        }
    }

    adjustWidth() {
        this.searchInput.value ? this.searchInput.size = this.searchInput.value.length + 1 : this.getSelectedValues().length ? (this.searchInput.placeholder = "", this.searchInput.size = 1) : (this.searchInput.size = this.placeholder.length, this.searchInput.placeholder = this.placeholder)
    }

    resetSearchInput() {
        this.searchInput.value = "", this.adjustWidth()
    }

    getSelectedValues() {
        let e = this.selectElement.querySelectorAll("option:checked");
        return Array.from(e).map(e => e.value)
    }

    showSuggestions() {
        this.dropElement.classList.contains("show") || this.dropElement.classList.add("show"), this.dropElement.style.left = this.searchInput.offsetLeft + "px";
        let e = this.searchInput.value.toLocaleLowerCase(), t = this.getSelectedValues(),
            s = this.dropElement.querySelectorAll("li"), l = !1, i = null;
        for (let n = 0; n < s.length; n++) {
            let r = s[n], o = r.innerText.toLocaleLowerCase(), a = r.querySelector("a");
            a.classList.remove(ACTIVE_CLASS), -1 == t.indexOf(a.getAttribute(VALUE_ATTRIBUTE)) ? -1 !== o.indexOf(e) ? (r.style.display = "list-item", l = !0, i || (i = r)) : r.style.display = "none" : r.style.display = "none"
        }
        l || this.dropElement.classList.remove("show"), i ? (this.holderElement.classList.contains("is-invalid") && this.holderElement.classList.remove("is-invalid"), i.querySelector("a").classList.add(ACTIVE_CLASS)) : this.allowNew || this.holderElement.classList.add("is-invalid")
    }

    hideSuggestions(e) {
        this.dropElement.classList.contains("show") && this.dropElement.classList.remove("show"), this.holderElement.classList.contains("is-invalid") && this.holderElement.classList.remove("is-invalid")
    }

    getActiveSelection() {
        return this.dropElement.querySelector("a." + ACTIVE_CLASS)
    }

    removeActiveSelection() {
        let e = this.getActiveSelection();
        e && e.classList.remove(ACTIVE_CLASS)
    }

    removeLastItem() {
        let e = this.containerElement.querySelectorAll("span");
        if (!e.length) return;
        let t = e[e.length - 1];
        this.removeItem(t.getAttribute(VALUE_ATTRIBUTE))
    }

    firstStart(e, t) {
        let type = this.selectElement.parentElement.lastElementChild.innerText.replace(' *', '');
        var items = this.getSelectedValues();

        t || (t = e);
		var img = document.createElement('img');
		img.setAttribute('src','IMG/close_black_24dp.svg');
		img.setAttribute('alt', 'delete icon');
		img.style.height= "18px";
		img.style.width= "18px";
        let s = document.createElement("span");
        s.classList.add("badge"), s.classList.add("tag"), s.classList.add("me-2"), s.setAttribute(VALUE_ATTRIBUTE, t), s.innerText = e, this.containerElement.insertBefore(s, this.searchInput);
		s.appendChild(img);
		img.addEventListener("click", e=> {
			 this.removeItem(s.getAttribute(VALUE_ATTRIBUTE));
		})
        if (type.toUpperCase() === "TECHNOLOGIEN") {
            fetchTechnologiesPracticeplace(items);
        } else {
            fetchRequirementsPracticeplace(items);
        }
    }

    addItem(e, t) {

        if (firstStart) {
            this.firstStart(e,t);
        } else {
            let type = this.selectElement.parentElement.lastElementChild.innerText.replace(' *', '');
            var items = this.getSelectedValues();
            var tagsLength = this.getSelectedValues().length;


            if (tagsLength <= 9) {
                if(e !== '' && e !== ' ') {
                    t || (t = e);
					var img = document.createElement('img');
					img.setAttribute('src','IMG/close_black_24dp.svg');
					img.setAttribute('alt', 'delete icon');
					img.style.height= "18px";
					img.style.width= "18px";
                    let s = document.createElement("span");
                    s.classList.add("badge"), s.classList.add("tag"), s.classList.add("me-2"), s.setAttribute(VALUE_ATTRIBUTE, t), s.innerText = e, this.containerElement.insertBefore(s, this.searchInput);
                    s.appendChild(img);
					img.addEventListener("click", e=> {
						 this.removeItem(s.getAttribute(VALUE_ATTRIBUTE));
					})
					let l = this.selectElement.querySelector('option[value="' + t + '"]');
                    l = document.createElement("option"), l.value = t, l.innerText = e, l.setAttribute("selected", "selected"), this.selectElement.appendChild(l);
                }
            } else {
                const feedbackElement = document.getElementById("feedback");

                let feedback = document.createElement("div");

                feedback.id = 'feedbackTag';
                feedback.className = 'alert alert-danger';
                feedback.setAttribute('role', 'alert');

                feedbackElement.append(feedback);


                feedback.innerHTML = 'Die max. Anzahl an ' + type + ' wurde erreicht';

                setTimeout(function () {
                    $('#feedbackTag').fadeOut('slow');
                    setTimeout(function () {
                        feedbackElement.removeChild(feedback);
                    }, 500);
                }, 2000);
            }

            var itemsT = Array.from(items);
            itemsT.push(t);

            if (type.toUpperCase() === "TECHNOLOGIEN") {
                fetchTechnologiesPracticeplace(itemsT);
            } else {
                fetchRequirementsPracticeplace(itemsT);
            }
        }
    }

    removeItem(e) {
        let type = this.selectElement.parentElement.lastElementChild.innerText.replace(' *', '');
        let t = this.containerElement.querySelector("span[" + VALUE_ATTRIBUTE + '="' + e + '"]');
        if (!t) return;
        t.remove();
        let s = this.selectElement.querySelector('option[value="' + e + '"]');
        s.remove();

        if (type.toUpperCase() === "TECHNOLOGIEN") {
            fetchTechnologiesPracticeplace(this.getSelectedValues());
        } else {
            fetchRequirementsPracticeplace(this.getSelectedValues());
        }
    }
}
export default Tags;