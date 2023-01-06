package com.syed.authserver.security;

import com.syed.authserver.entity.Client;
import com.syed.authserver.repository.ClientRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final ClientRepository clientRepository;

    public ClientDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        Client client = clientRepository.findByClientId(s)
                .orElseThrow(() -> new ClientRegistrationException("Client with id " + s + " not found"));

        return new SecurityClient(client);
    }
}
