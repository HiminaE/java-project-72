@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.utils.Paths
@import hexlet.code.utils.TimeUtils
@param UrlsPage page

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
        <h3>Сайты</h3>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th scope="col-1">ID</th>
                <th scope="col">Имя сайта</th>
                <th scope="col-2">Последняя проверка</th>
                <th scope="col-1">Код ответа</th>
            </tr>
            </thead>
            <tbody>
            @for(var url : page.getUrls())
                <tr>
                    <th scope="row">${url.getId()}</th>
                    <th><a href="${Paths.urlsIdPath(url.getId())}">${url.getName()}</a></th>
                    @if(page.getLastChecks().containsKey(url.getId()))
                        <td>${TimeUtils.getFormattedData(page.getLastChecks().get(url.getId()).getCreatedAt())}</td>
                        <td>${page.getLastChecks().get(url.getId()).getStatusCode()}</td>
                    @endif
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`
)