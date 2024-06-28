@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.utils.TimeUtils
@param UrlPage page


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
    <div class="container-lg mt-5">
        <h3>Сайт: ${page.getUrl().getName()}</h3>
        <table class="table table-primary table-striped">
            <tbody>
            <tr>
                <th>ID</th>
                <th>${page.getUrl().getId()}</th>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${TimeUtils.getFormattedData(page.getUrl().getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="container-lg mt-5">
        <h4>Проверки</h4>
        <form action="/urls/${page.getUrl().getId()}/checks" method="post">
            <button type="submit">Запустить проверку</button>
        </form>
        <table class="table table-primary table-striped">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Код ответа</th>
                <th scope="col">title</th>
                <th scope="col">h1</th>
                <th scope="col">description</th>
                <th scope="col">Дата проверки</th>
            </tr>
            </thead>
            @if(page.getUrlChecks() != null)
                <tbody>
                @for(var check : page.getUrlChecks())
                    <tr>
                        <th scope="row">${check.getId()}</th>
                        <td><a>${check.getStatusCode()}</a></td>
                        <td><a>${check.getTitle()}</a></td>
                        <td><a>${check.getH1()}</a></td>
                        <td><a>${check.getDescription()}</a></td>
                        <td><a>${TimeUtils.getFormattedData(check.getCreatedAt())}</a></td>
                    </tr>
                @endfor
                </tbody>
            @endif
        </table>
    </div>
`
)