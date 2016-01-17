# Comp132Assignment5

## Add a new ADS Root Key if one has NOT been added in domain 
## 
## CMD Below adds the RootKey to the domain , however there is a 10 hour wait to allow 
## the key to be replicated to all DCs 
##
## Add-KdsRootKey -EffectiveImmediately. 
##
## If you only have a single DC or for testing ,you can ffwd the 10 hours as per below
##
## Add-KdsRootKey -EffectiveTime ((get-date)).addhours(-10) 
##
## Create a new managed service account 
## Windows 2012 and greater MSA's by default are groupMSA's (gMSA) not singleMSA (sMSA)
## so they can be associated with more than one computer account in the Domain as pwd
## generation is moved to the DC 
## 
## New-ADServiceAccount -Name gMSA-VEEAM -SamAccountName gMSA-VEEAM -PrincipalsAllowedToRetrieveManagedPassword gMSA-VEEAM-Users 
## -DNSHostName WIN-HIUJ9T2SE44.thisdigitalife.net (name of DC controller which holds the KDS Master Key 
## -msKds-ProvRootKey ( an ad attribute on the dc object I expect) 
##
## 
## **** MAKE SURE TARGET SERVER is in the group that was specified in -PrincipalsAllowedToRetrieveManagedPassword group (gMSA-VEEAM-Users)
##
## Link a computer to an MSA
## 
## Add-ADComputerServiceAccount -identity TDL-FPS-01 -ServiceAccount gMSA-VEEAM
##
## ** REST IS NOW TO BE DONE ON THE SERVERS THAT WILL USE THE gMSA **
## 
## Install the AD Service account on the linked computer object , has to be done on target 
## (ie TDL-FPS-01) 
## 
## If AD Powershell module is not installed on the target system 
## 
## Import-Module Server-Manager 
## Add-WindowsFeature RSAT-AD-Powershell 
## 
## Install-ADServiceAccount -Identity 'gMSA-VEEAM' (note the quotes) 
##
## Confirm that the Account is Installed 
## 
## Get-ADServiceAccount -Identity 'gMSA-VEEAM' 
## Should return $true. 
## 
## As a test you can create a dummy IIS Application pool 
## Advanced Settings , Application Pool Identity , Set Custom use DOMAIN\gMSA-VEEAM$ (note the $) 
## Do not enter password 
## OK and dialog box should close 


