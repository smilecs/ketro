package com.past3.ketro.data.entities

import com.past3.ketro.domain.entities.Items
import com.past3.ketro.kcore.model.KMapper

class ResponseMapper : KMapper<ResponseItems, Items>() {

    override fun mapFrom(from: ResponseItems): Items =
            Items(
                    from.login,
                    from.id,
                    from.node_id,
                    from.avatar_url,
                    from.gravatar_url,
                    from.url,
                    from.html_url,
                    from.followers_url,
                    from.subscriptions_url,
                    from.organizations_url,
                    from.repos_url,
                    from.received_events_url,
                    from.type,
                    from.score
            )

}