@import hexlet.code.dto.urls.MainPage
@param MainPage page

@template.layout.page(
content = @`
    @if(page.getFlash() != null)
        @if(page.getFlashType().equals("error"))
            <div class="alert alert-warning" role="alert">
        @else
            <div class="alert alert-primary" role="alert">
        @endif
        ${page.getFlash()}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Закрыть"></button>
        </div>
    @endif
    <div class="container text-center">
        <div class="container-fluid bg-dark p-5">
            <div class="row">
                <div class="col">
                    <h1>Анализатор страниц</h1>
                </div>
            </div>
            <form action="/urls" method="post" class="rss-form text-body">
                <div class="mb-3">
                    <label class="form-label">Бесплатно проверяйте сайты на SEO пригодность</label>
                    <div class="col-auto">
                        <input name="url" type="text" class="form-control form-control-lg" placeholder="Ссылка">
                    </div>
                    <div class="form-text">Пример: https://www.example.com</div>
                    <div class="col-auto">
                        <button class="btn btn-primary col-auto" type="submit">Проверить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
`
)