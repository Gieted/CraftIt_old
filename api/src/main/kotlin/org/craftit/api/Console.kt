package org.craftit.api

import org.craftit.api.resources.commands.CommandIssuer

interface Console : CommandIssuer, ChatParticipant {

}
