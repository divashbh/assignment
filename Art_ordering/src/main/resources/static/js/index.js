const carousel = document.querySelector('.carousel');
const slides = document.querySelectorAll('.slide');
const prevBtn = document.querySelector('.prev-btn');
const nextBtn = document.querySelector('.next-btn');
const dots = document.querySelectorAll('.dot');
const autoSliderDelay = 5000; // in ms

let slideIndex = 1;
let autoSliderInterval;

// Show the first slide and activate the first dot
showSlide(slideIndex);

// Add event listeners for prev/next buttons and dots
prevBtn.addEventListener('click', () => {
    showSlide(slideIndex -= 1);
});
nextBtn.addEventListener('click', () => {
    showSlide(slideIndex += 1);
});
dots.forEach((dot) => {
    dot.addEventListener('click', () => {
        const slide = dot.getAttribute('data-slide');
        showSlide(slideIndex = slide);
    });
});

// Start the auto slider
startAutoSlider();

// Pause the auto slider when the mouse enters the carousel
carousel.addEventListener('mouseenter', () => {
    clearInterval(autoSliderInterval);
});

// Resume the auto slider when the mouse leaves the carousel
carousel.addEventListener('mouseleave', () => {
    startAutoSlider();
});

function showSlide(index) {
    // Wrap slide index around if it goes out of bounds
    if (index < 1) {
        index = slides.length;
    } else if (index > slides.length) {
        index = 1;
    }

    // Hide all slides and deactivate all dots
    slides.forEach((slide) => {
        slide.classList.remove('active');
    });
    dots.forEach((dot) => {
        dot.classList.remove('active');
    });

    // Show the current slide and activate the corresponding dot
    slides[index - 1].classList.add('active');
    dots[index - 1].classList.add('active');

    // Set the slide index to the current index
    slideIndex = index;

    // Translate the carousel to show the current slide
    carousel.style.transform = `translateX(-${(slideIndex - 1) * 100}%)`;
}

function startAutoSlider() {
    autoSliderInterval = setInterval(() => {
        showSlide(slideIndex += 1);
    }, autoSliderDelay);
}