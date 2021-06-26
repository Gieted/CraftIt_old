package org.craftit.api.resources.entities

import org.craftit.api.chat.ChatParticipant
import org.craftit.api.resources.Resource
import org.craftit.api.resources.commands.CommandIssuer

interface Entity :
    Resource,
    Mortal,
    CommandIssuer,
    ChatParticipant
