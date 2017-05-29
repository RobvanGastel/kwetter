<#import "masterTemplate.ftl" as layout />

<@layout.masterTemplate title="Timeline">

<div class="row">
    <div class="col-xs-11">
        <h3>${pageTitle}</h3>

        <#if user??>
            <#if profileUser?? && user.id != profileUser.id>
                <div class="pull-right">
                    <#if followed>
                        <a class="btn btn-warning" href="/t/${profileUser.username}/unfollow">Unfollow User</a>
                    <#else>
                        <a class="btn btn-primary" href="/t/${profileUser.username}/follow">Follow User</a>.
                    </#if>
                </div>
            <#elseif pageTitle != 'Public Timeline'>
                        <h4>Send Tweet</h4>
                        <form class="form-horizontal" action="/message" method="post">
                            <div class="input-group">
                                <input type="text" name="text" class="form-control" required/>
                                <span class="input-group-btn">
                                    <button class="btn btn-primary" type="submit"> Send </button>
                                </span>
                            </div>
                        </form>
                    </div>
                </div>
            </#if>
        </#if>
    </div>
</div>

<div class="row">
    <div class="col-xs-10 col-xs-offset-1">
        <div id="media-list" class="row">
            <#if messages??>
                <#list messages as message>
                    <hr/>

                    <div class="media">
                        <a class="pull-left" href="/t/${message.username}">
                            <img class="media-object" src="${message.avatar}"/>
                        </a>
                        <div class="media-body">
                            <h4 class="media-heading">
                                <a href="/t/${message.username}">
                                ${message.username}
                                </a>
                            </h4>${message.text} <br/>

                            <small>${message.pubDateStr}</small>
                        </div>
                    </div>
                <#else>
                    <hr/>
                    <div class="well">
                        There're no messages so far.
                    </div>
                </#list>
            <#else>
                <hr/>
                <div class="well">
                    There're no messages so far.
                </div>
            </#if>
        </div>
    </div>
</div>

</@layout.masterTemplate>